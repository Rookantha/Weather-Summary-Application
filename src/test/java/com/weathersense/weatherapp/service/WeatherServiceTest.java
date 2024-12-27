import com.weathersense.weatherapp.model.WeatherResponse;
import com.weathersense.weatherapp.model.WeatherSummary;
import com.weathersense.weatherapp.service.WeatherServiceImpl;
import com.weathersense.weatherapp.config.WeatherApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

class WeatherServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WeatherApiConfig weatherApiConfig;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock WebClient.Builder to return a WebClient instance through the chain
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder); // Return the builder itself
        when(webClientBuilder.build()).thenReturn(webClient); // Build and return the WebClient instance
    }

    @Test
    void testGetWeatherSummary() {
        // Prepare mock data
        WeatherResponse weatherResponse = new WeatherResponse();
        WeatherResponse.Forecast forecast = new WeatherResponse.Forecast();
        forecast.setDt_txt("2024-12-21 12:00:00");
        WeatherResponse.Main main = new WeatherResponse.Main(285.15); // Temperature in Kelvin
        forecast.setMain(main);
        weatherResponse.setList(Collections.singletonList(forecast));

        // Mock WebClient behavior
        when(webClient.get()).thenReturn(requestHeadersUriSpec);  // Simplified mock
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(WeatherResponse.class)).thenReturn(Mono.just(weatherResponse));

        // Call the service method
        WeatherSummary summary = weatherService.getWeatherSummary("Test City").join(); // Async method

        // Verify the result
        assertEquals("Test City", summary.getCity());
        assertEquals(-287.0, summary.getAverageTemperature(), 0.1); // 285.15K = 12°C, in Celsius it's 12 - 273.15 = -287.0
    }
}
