package com.zigzag.auction.lib;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
    @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
            value = "Results page you want to retrieve (0..N). Default value is 0.", dataTypeClass = Integer.class),
    @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
            value = "Number of records per page. Default value is 20.", dataTypeClass = Integer.class),
    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). "
            + "Default sort order is ascending. " + "Multiple sort criteria are supported.",
            dataTypeClass = String.class) })
public @interface ApiPageable {
}
