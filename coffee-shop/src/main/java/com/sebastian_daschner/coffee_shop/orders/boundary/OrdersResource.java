package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.EntityBuilder;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.ValidOrder;

import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject
    CoffeeShop coffeeShop;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @GET
    public JsonArray getOrders() {
        List<Order> orders = coffeeShop.getOrders();
        return entityBuilder.buildOrders(orders, uriInfo, request);
    }

    @PUT
    @Path("{id}")
    public void updateOrder(@PathParam("id") UUID id, JsonObject jsonObject) {
        Order order = entityBuilder.buildOrder(jsonObject);
        coffeeShop.updateOrder(id, order);
    }

    @GET
    @Path("{id}")
    public JsonObject getOrder(@PathParam("id") UUID id) {
        final Order order = coffeeShop.getOrder(id);

        if (order == null)
            throw new NotFoundException();

        return entityBuilder.buildOrder(order);
    }

    @POST
    public Response createOrder(@Valid @ValidOrder JsonObject json) {
        final Order order = entityBuilder.buildOrder(json);

        coffeeShop.createOrder(order);

        return Response.created(buildUri(order)).build();
    }

    private URI buildUri(Order order) {
        return uriInfo.getBaseUriBuilder()
                .host(request.getServerName())
                .port(request.getServerPort())
                .path(OrdersResource.class)
                .path(OrdersResource.class, "getOrder")
                .build(order.getId());
    }

}
