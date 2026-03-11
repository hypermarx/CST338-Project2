public class TemperatureCoverters {
    static double FarenheitToCelsius(double f){
        return (f - 32) * 5.0 / 9.0;

    }
    static double CelsiusToFarenheit(double c){
        return (c * 9.0/5.0) + 32;
    }

}
