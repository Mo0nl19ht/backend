package ganggang3.gang.dto;

import ganggang3.gang.domain.Category;
import ganggang3.gang.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDtoVlog {
    private String name;
    private double location_x;
    private double location_y;
    private Category category;

    public static PlaceDtoVlog of (Place P){
        return new PlaceDtoVlog(
                P.getName(),
                P.getLocation_x(),
                P.getLocation_y(),
                P.getCategory()
        );
    }
}
