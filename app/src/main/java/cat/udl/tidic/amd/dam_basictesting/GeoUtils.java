package cat.udl.tidic.amd.dam_basictesting;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoUtils {

    private Geocoder geocoder;

    public GeoUtils(Context context){
        this.geocoder = new Geocoder(context, Locale.getDefault());
    }

    public GeoUtils(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    /**
     * This method returns the country as a String where the latitude and longitude belongs to.
     * @param lat
     * @param lon
     * @return
     * @throws IOException
     */
    public String getCountry(double lat, double lon) throws IOException {
        List<Address> addresses = this.geocoder.getFromLocation(lat, lon, 1);
        return (addresses.size() > 0) ? addresses.get(0).getCountryName() : null;
    }
}
