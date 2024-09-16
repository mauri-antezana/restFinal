package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.RestEndpoints;
import entities.Booking;
import entities.BookingDates;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.it.Ma;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import utils.Request;

import java.util.List;

import static org.hamcrest.Matchers.not;

public class BookingSteps {
    Response response;

    @Then("I verify the status code is {int}")
    public void verifyStatusCode(int statusCode){response.then().assertThat().statusCode(statusCode);}

    @And("I verify that the body does not have size {int}")
    public void verifyResponseSize(int size){response.then().assertThat().body("size()",not(size));}

    @And("I verify the following data in the body response")
    public void verifyBookingData(DataTable bookingData){
        List<String> data = bookingData.transpose().asList(String.class);

        response.then().assertThat().body("booking.firstname", Matchers.equalTo(data.get(0)));
        response.then().assertThat().body("booking.lastname", Matchers.equalTo(data.get(1)));
        response.then().assertThat().body("booking.totalprice", Matchers.equalTo(Integer.parseInt(data.get(2))));
        response.then().assertThat().body("booking.depositpaid", Matchers.equalTo(Boolean.parseBoolean(data.get(3))));
        response.then().assertThat().body("booking.bookingdates.checkin", Matchers.equalTo(data.get(4)));
        response.then().assertThat().body("booking.bookingdates.checkout", Matchers.equalTo(data.get(5)));
        response.then().assertThat().body("booking.additionalneeds", Matchers.equalTo(data.get(6)));
    }

    @And("I verify the following data in the body update response")
    public void verifyUpdateBookingData(DataTable bookingData){
        List<String> data = bookingData.transpose().asList(String.class);

        response.then().assertThat().body("firstname", Matchers.equalTo(data.get(0)));
        response.then().assertThat().body("lastname", Matchers.equalTo(data.get(1)));
        response.then().assertThat().body("totalprice", Matchers.equalTo(Integer.parseInt(data.get(2))));
        response.then().assertThat().body("depositpaid", Matchers.equalTo(Boolean.parseBoolean(data.get(3))));
        response.then().assertThat().body("bookingdates.checkin", Matchers.equalTo(data.get(4)));
        response.then().assertThat().body("bookingdates.checkout", Matchers.equalTo(data.get(5)));
        response.then().assertThat().body("additionalneeds", Matchers.equalTo(data.get(6)));
    }

    // GET STEPS
    @Given("I perform a GET call to get all the booking ids")
    public void getAllBookings(){response = Request.get(RestEndpoints.GET_BOOKING_IDS);}

    @Given("I perform a GET call to the get by id endpoint with the id {string}")
    public void getBookingById(String id){ response = Request.getById(RestEndpoints.GET_BOOKING,id);}

    // DELETE STEPS
    @Given("I perform a DELETE call to a booking endpoint with id {string}")
    public void deleteBooking(String id){response = Request.delete(RestEndpoints.DELETE_BOOKING,id);}

    //POST STEPS
    @Given("I perform a POST call to the create endpoint with the following data")
    public void createBooking(DataTable dataTable) throws JsonProcessingException {
        List<String> data = dataTable.transpose().asList(String.class);
        Booking booking = new Booking();
        BookingDates bookingDates = new BookingDates();

        booking.setFirstname(data.get(0));
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
            bookingDates.setCheckin(data.get(4));
            bookingDates.setCheckout(data.get(5));
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds(data.get(6));

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        response = Request.post(RestEndpoints.CREATE_BOOKING,payload);
    }

    //PUT STEPS
    @Given("I perform a PUT call to the update endpoint with the id {string} and the following data")
    public void updateBooking(String id, DataTable dataTable) throws JsonProcessingException {
        List<String> data = dataTable.transpose().asList(String.class);
        Booking booking = new Booking();
        BookingDates bookingDates = new BookingDates();

        booking.setFirstname(data.get(0));
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
            bookingDates.setCheckin(data.get(4));
            bookingDates.setCheckout(data.get(5));
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds(data.get(6));

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        System.out.println(payload);

        response = Request.put(RestEndpoints.UPDATE_BOOKING,id,payload);
    }
}
