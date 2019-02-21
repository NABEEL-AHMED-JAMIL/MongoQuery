package com.ballistic.doa.query;

import com.google.gson.Gson;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.ValidationException;
import java.util.*;
import static com.ballistic.doa.query.LocalQuery.QueryOption.*;

//https://www.programcreek.com/java-api-examples/index.php?api=org.mongodb.morphia.query.Criteria
//https://www.programcreek.com
public class LocalQuery {

    private Set<FieldQuery> filter; // filter (=,!=,<,>,<=,>=,!) // done
    private Boolean isFilterApply = true; // isFilter (false=>use for and| true=> use for or) // done
    private Set<String> projection; // projection(key=attribute) // done
    private Map<String, Integer> sort; // sort(key=attribute, value=(0,1) // done

    public LocalQuery() {}

    public Set<FieldQuery> getFilter() { return filter; }
    public void setFilter(Set<FieldQuery> filter) { this.filter = filter; }

    public Boolean getFilterApply() { return isFilterApply; }
    public void setFilterApply(Boolean filterApply) { isFilterApply = filterApply; }

    public Set<String> getProjection() { return projection; }
    public void setProjection(Set<String> projection) { this.projection = projection; }

    public Map<String, Integer> getSort() { return sort; }
    public void setSort(Map<String, Integer> sort) { this.sort = sort; }

    public void createQuery(Query<?> query) {
        try {
            if(query != null) {
                // filter (diff way)
                Set<Criteria> criteria = new HashSet<>();
                if(this.getFilter()!= null && this.getFilter().size() > 0) {
                    this.getFilter().stream().forEach(fieldQuery -> {
                        if(fieldQuery.getField() != null) {
                            if(fieldQuery.getOperation().equals(EQUAL.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).equal(fieldQuery.getValue()));
                            } else if(fieldQuery.getOperation().equals(LESS_THEN.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).lessThan(fieldQuery.getValue()));
                            } else if(fieldQuery.getOperation().equals(GREATER_THEN.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).greaterThan(fieldQuery.getValue()));
                            } else if(fieldQuery.getOperation().equals(LESS_THEN_EQUAL.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).lessThanOrEq(fieldQuery.getValue()));
                            } else if(fieldQuery.getOperation().equals(GREATER_THEN_EQUAL.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).greaterThanOrEq(fieldQuery.getValue()));
                            } else if(fieldQuery.getOperation().equals(NOT_EQUAL.option)) {
                                criteria.add(query.criteria(fieldQuery.getField()).notEqual(fieldQuery.getValue()));
                            } else if (fieldQuery.getOperation().equals(IN.option)) {
                                if(fieldQuery.getValue() instanceof Set) {
                                    criteria.add(query.criteria(fieldQuery.getField()).in((Iterable<?>)fieldQuery.getValue()));
                                }
                            } else if(fieldQuery.getOperation().equals(NOT_IN.option)) {
                                if(fieldQuery.getValue() instanceof Set) {
                                    criteria.add(query.criteria(fieldQuery.getField()).notIn((Iterable<?>)fieldQuery.getValue()));
                                }
                            }
                        }
                    });
                    // default it's true
                    if(this.getFilterApply()) {
                        query.or(criteria.toArray(new Criteria[criteria.size()]));
                    } else {
                        query.and(criteria.toArray(new Criteria[criteria.size()]));
                    } // not and nor no need yet
                }
                // projection(mean which field want to show)
                if(this.getProjection() != null && this.getProjection().size() > 0) {
                    this.getProjection().stream().forEach(pro -> {
                        query.project(pro, true);
                    });
                }
                // sorting(- mean descending)
                if(this.getSort()!= null && this.getSort().size() > 0) {
                    StringBuilder order = new StringBuilder();
                    this.getSort().forEach((key, value) -> {
                        if(!key.equals("")) { order.append((value < 0) ? "-"+key : key); }
                    });
                    query.order(String.valueOf(order));
                }
                System.out.println("Query :- " + query.toString());
            }
        }catch (ValidationException ex) {
            System.err.println("Error :- " + ex.getLocalizedMessage());
            throw ex;
        }
    }

    @Override
    public String toString() { return new Gson().toJson(this); }

    public enum QueryOption {

        EQUAL(0, "="),
        LESS_THEN(1, "<"),
        GREATER_THEN(2, ">"),
        LESS_THEN_EQUAL(3, "<="),
        GREATER_THEN_EQUAL(4, ">="),
        NOT_EQUAL(5, "!="),
        IN(6, "In"),
        NOT_IN(7, "nIn");

        Integer option;
        String optionValue;

        QueryOption(int option, String optionValue) {
            this.option = option;
            this.optionValue = optionValue;
        }

        public Integer getOption() { return option; }
        public String getOptionValue() { return optionValue; }

        public static String getMatchOptionValue(Integer option) {
            String optionValue = null;
            switch (option) {
                case 0:
                    optionValue = EQUAL.getOptionValue();
                    break;
                case 1:
                    optionValue = LESS_THEN.getOptionValue();
                    break;
                case 2:
                    optionValue = GREATER_THEN.getOptionValue();
                    break;
                case 3:
                    optionValue = LESS_THEN_EQUAL.getOptionValue();
                    break;
                case 4:
                    optionValue = GREATER_THEN_EQUAL.getOptionValue();
                    break;
                case 5:
                    optionValue = NOT_EQUAL.getOptionValue();
                    break;
                case 6:
                    optionValue = IN.getOptionValue();
                    break;
                case 7:
                    optionValue = NOT_IN.getOptionValue();
                    break;
                default: // default will equal
                    optionValue = EQUAL.getOptionValue();
                    break;
            }
            return optionValue;
        }

        @Override
        public String toString() { return new Gson().toJson(this); }

    }
}
