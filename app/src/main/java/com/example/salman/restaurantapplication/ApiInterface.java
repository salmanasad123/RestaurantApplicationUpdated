package com.example.salman.restaurantapplication;

import android.text.Editable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Salman on 5/31/2018.
 */

public interface ApiInterface {

    /**
     * Get All Restaurants from database through this route via GET REQUEST
     * Response will be in List
     */

    @GET("api/restaurants")
    Call<List<Restaurant>> getRestaurants();

    // @GET("api/restaurantsfeedback")
    // Call<List<Restaurant>> getRestaurants();

    @GET("api/restaurant/categories")
    Call<List<RestaurantCategories>> getRestaurantCategories();

    /**
     * Pass the id of Restaurant to get the Menu Categories of that Restaurant via Get Request
     * with this route
     */

    @GET("api/restaurant/{id}/categories")
    Call<List<RestaurantCategories>> getRestaurantMenuCategories(@Path("id") int id);

    /**
     * Get Products of restaurant categories, Pass category ID and Restaurant ID to show
     * products of a specific Restaurant.
     */

    @GET("api/category/{category_id}/product/{restaurant_id}")
    Call<List<GetMenuProducts>> getMenuProducts(@Path("category_id") int category_id, @Path("restaurant_id") int restaurant_id);
    //can use @ Field as well

    /**
     * Get CartItems Table with Get Request
     * Every Restaurant will have a separate cart maintained
     * Restaurant ID being passed as parameter to Get Route to get cart of that specific restaurant
     */

    // Route to get cart items of every restaurant
    // Maintaining Cart for every restaurant will allow not to order items from multiple restaurants.
    @GET("api/cartitems/{id}")
    Call<List<Cart>> showCart(@Path("id") int id);


    // Route to get cart items of individual Customers and individual restaurant. Maintaining cart
    // for every restaurant will allow not to order items from multiple restaurants.

    //  @GET("api/cartitems/{restaurant_id}/{customer_id}")
    //  Call<List<Cart>> getCart(@Path("restaurant_id") int restaurant_id, @Path("customer_id") int customer_id);

    @GET("api/cartitems/{customer_id}/{restaurant_id}")
    Call<List<Cart>> getCart(@Path("customer_id") int customer_id, @Path("restaurant_id") int restaurant_id);

    /**
     * Post Route to Save the items added to Cart to Database
     **/

    @POST("api/cartitems")
    @FormUrlEncoded
    Call<Cart> addToCart(@Field("ProductID") int ProductID,
                         // @Field("ProductName") String ProductName,
                         // @Field("ProductPrice") int ProductPrice,
                         @Field("quantity") int quantity,
                         @Field("ShoppingCartID") int ShoppingCartID,
                         @Field("RestaurantID") int RestaurantID);

    // @Field("CustomerID") int CustomerID


    @PUT("api/cartitems/{id}")
    Call<Cart> updateCart(@Path("id") int id, @Body Cart cart);

    @DELETE("api/cartitems/{id}")
    Call<Cart> deleteItemFromCart(@Path("id") int id);


    @POST("api/shoppingcart")
    @FormUrlEncoded
    Call<ShoppingCart> postToShoppingCart(@Field("CustomerID") int CustomerID);


    /**
     * FeedBack Routes /////////////////////////////////////////////////////////////
     */


    @POST("api/feedback")
    @FormUrlEncoded
    Call<Feedback> sendFeedback(@Field("Rating") int Rating,
                                @Field("comment") String comment,
                                @Field("RestaurantID") int RestaurantID

    );

    @GET("api/feedback/{id}")
    Call<List<Feedback>> getFeedback(@Path("id") int id);

    @POST("api/register")
    @FormUrlEncoded
    Call<Customer> registerCustomer(@Field("Name") String Name,
                                    @Field("CustomerEmail") String CustomerEmail,
                                    @Field("CustomerPhone") Editable CustomerPhone,
                                    @Field("CustomerAddress") String CustomerAddress,
                                    @Field("City") String City,
                                    @Field("Password") String Password);


    @POST("api/login")
    @FormUrlEncoded
    Call<List<Customer>> postLoginData(@Field("CustomerEmail") String CustomerEmail,
                                       @Field("Password") String Password
    );

    @PUT("api/profile/{id}")
    Call<Customer> updateProfile(@Path("id") int id, @Body Customer customer);


    @GET("api/profile/{id}")
    Call<List<Customer>> getProfile(@Path("id") int id);

    @POST("api/orderdetails")
    @FormUrlEncoded
    Call<DetailsOrder> PostOrderDetails(@Field("ProductName") String ProductName,
                                        @Field("quantity") int quantity,
                                        @Field("OrderType") String OrderType,
                                        @Field("TotalAmount") int TotalAmount,
                                        @Field("CustomerID") int CustomerID,
                                        @Field("RestaurantID") int RestaurantID,
                                        @Field("ShoppingCartID") int ShoppingCartID);

    @GET("api/orderdetails/{customer_id}")
    Call<List<OrderHistory>> getOrderHistory(@Path("customer_id") int customer_id);

    @DELETE("api/cartitem/{id}")
    Call<Cart> EmptyCart(@Path("id") int id);

    @GET("api/feedback/rating/{id}")
    Call<Double> AvgRating(@Path("id") int id);

}


