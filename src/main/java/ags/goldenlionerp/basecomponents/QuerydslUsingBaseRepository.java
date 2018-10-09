package ags.goldenlionerp.basecomponents;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

public interface QuerydslUsingBaseRepository<T extends EntityPath<?>> 
	extends QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<T>{

	@Override
	default void customize(QuerydslBindings bindings, T root) {
		bindings.bind(String.class)
				.first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
