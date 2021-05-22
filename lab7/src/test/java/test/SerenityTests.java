package test;

import endpoints.DoggieAppEndpoint;
import io.restassured.response.Response;
import models.Dog;
import models.Route;
import models.User;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class SerenityTests {
    public static final DoggieAppEndpoint appEndpoint = new DoggieAppEndpoint();

    @Title("User create & get")
    @Test
    public void createUser() {
        final String email = "email123@gmail.com";
        Response userRes = appEndpoint.createUser(email, "secret");

        final Long createdUserId = userRes.body().as(User.class).id;
        User createdUser = appEndpoint.getUserById(String.valueOf(createdUserId)).as(User.class);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat( createdUser.email).as("email").isEqualTo(email);
        assertions.assertAll();
    }

    @Title("Dog create & get")
    @Test
    public void createDog() {
        Dog dog = new Dog("Jo", "poodle");
        Response res = appEndpoint.createDog(dog);
        long dogId = res.body().as(Dog.class).getId();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(dogId).as("Id").isNotNull();
        assertions.assertAll();
    }
    /*
    @Title("Route create")
    @Test
    public void createRoute() {
        final String polyline = "oeq~F|zgvOpsEofEs{CiqE";
        final Double lat = 50.78;
        final Double lng = 12.45;

        Response routeRes = appEndpoint.createRoute(polyline,lat, lng);
        long routeId = routeRes.body().as(Route.class).id;

        Route created = appEndpoint.getRouteById(routeId).as(Route.class);

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat( created.polyline).as("Line").isEqualTo(polyline);
        assertions.assertThat( created.lat).as("Lat").isEqualTo(lat);
        assertions.assertAll();
    }
    */

    @Test
    @Title("User delete")
    public void deleteUser() {
        final String email = "email1234@gmail.com";
        Response userRes = appEndpoint.createUser(email, "secret");

        final Long userId = userRes.body().as(User.class).id;
        appEndpoint.deleteUserById(userId);

        Response userById = appEndpoint.getUserById(String.valueOf(userId));
        Assertions.assertThat(userById.getStatusCode()).isEqualTo(404);
    }
}
