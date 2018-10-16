package ags.goldenlionerp.application.item.uomconversion;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;

@Service
public class StandardUomConversionGraph {

	private StandardUomConversionRepository repo;
	
	private ValueGraph<String, BigDecimal> graph;
	
	public StandardUomConversionGraph(@Autowired StandardUomConversionRepository repo) {
		this.repo = repo;
		graph = initGraph();
	}
	
	private ValueGraph<String, BigDecimal> initGraph() {
		MutableValueGraph<String, BigDecimal> graph = ValueGraphBuilder.directed().build();
		
		Iterable<StandardUomConversion> all = repo.findAll();
		for (StandardUomConversion conv: all) {
			BigDecimal val = conv.getConversionValue();
			graph.putEdgeValue(conv.getUomFrom(), conv.getUomTo(), val);
			graph.putEdgeValue(conv.getUomTo(), conv.getUomFrom(), ONE.divide(val, val.scale(), HALF_UP));
		}
		
		return graph;
	}
	
	public Optional<BigDecimal> findConversionValue(String from, String to) {
		//find the conversion path
		List<String> path = findConversionPath(from, to);
		
		if (path.isEmpty())
			return Optional.empty();
		
		//find the values on each edge on the path, and multiply them all
		BigDecimal acc = ONE;
		for (int i = 0; i < path.size() - 1; i++) {
			acc = graph.edgeValue(path.get(i), path.get(i + 1)).get().multiply(acc);
		}
		return Optional.of(acc);
		//return findConversionValueInner(from, to, ONE, new ArrayList<>());
	}

	/**
	 * traverse the graph breadth-first to find the shortest path from the source to destination
	 * @param from
	 * @param to
	 * @return
	 */
	private List<String> findConversionPath(String from, String to){
		List<String> alreadyTraversed = new ArrayList<>();
		Deque<List<String>> paths = new ArrayDeque<>();
		paths.push(Arrays.asList(from));
		
		while(!paths.isEmpty()) {
			List<String> path = paths.pop();
			String lastNode = path.get(path.size() - 1);
			if (lastNode.equals(to))
				return path;
			
			for (String nextNode: graph.adjacentNodes(lastNode)) {
				if (alreadyTraversed.contains(nextNode))
					continue;
				alreadyTraversed.add(nextNode);
				
				List<String> nextPath = new ArrayList<>(path);
				nextPath.add(nextNode);
				paths.push(nextPath);
			}
		}
		
		return Collections.emptyList();
	}
	
}
