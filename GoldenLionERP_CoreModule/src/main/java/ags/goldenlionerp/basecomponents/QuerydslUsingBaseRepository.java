package ags.goldenlionerp.basecomponents;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

public interface QuerydslUsingBaseRepository<S, T extends EntityPath<?>> 
	extends QuerydslPredicateExecutor<S>, QuerydslBinderCustomizer<T>{

	@Override
	default void customize(QuerydslBindings bindings, T root) {
		bindings.bind(String.class)
				.all((StringPath path, Collection<? extends String> values) -> {
					BooleanBuilder predicate = new BooleanBuilder();
					values.forEach(val -> predicate.and(path.containsIgnoreCase(val)));
					return Optional.of(predicate);
				});
				
	}
}
