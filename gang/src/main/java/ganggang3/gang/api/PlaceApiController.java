package ganggang3.gang.api;

import ganggang3.gang.Service.PlaceService;
import ganggang3.gang.domain.Place;
import ganggang3.gang.domain.Place_Vlog;
import ganggang3.gang.domain.Vlog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;

    @GetMapping("/api/place/{station}/{category}")
    public Result findTop5(@PathVariable("station") int station_id, @PathVariable("category") int category_id){

        List<Place> findTop5 = placeService.findTOP5(station_id,category_id);

        List<PlaceDto> collect=new ArrayList<>();
        for(int i=0;i< findTop5.size();i++){
            Place P = findTop5.get(i);
            List<Place_Vlog> p_v_list = P.getPlace_vlogList();
            //Top5 place 의 vlog_list
            List<Vlog_Dto> vlog_list=new ArrayList<>();
            for(int j = 0; j< p_v_list.size(); j++){
                Place_Vlog pv = p_v_list.get(j);
                Vlog v = pv.getVlog();
                //Top5 place의 Vlog DTO를 할당
                vlog_list.add(
                        new Vlog_Dto(
                        v.getName(),
                        v.getUrl()
                ));
            }

            //Place Dto 할당
            PlaceDto pd = new PlaceDto(
                    P.getName(),
                    P.getLocationx(),
                    P.getLocationy(),
                    P.getExplanation(),
                    P.getAddress(),
                    vlog_list // 위에서 만들어준 place_vlog_dto
            );
            //리스트에 할당
            collect.add(pd);
        }
        return new Result(collect);
    }
//    FindTop5Response
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class PlaceDto{
        private String name;
        private double locationx;
        private double locationy;
        private String explanation;
        private String address;
        private List<Vlog_Dto> vlog_list;
    }

//    @Data
//    @AllArgsConstructor
//    static class Place_Vlog_Dto{
//        private PlaceDto_del place;
//        private Vlog_Dto vlog;
//
//    }
//    @Data
//    @AllArgsConstructor
//    static class PlaceDto_del{
//        private String name;
//        private double locationx;
//        private double locationy;
//        private String explanation;
//        private String address;
//
////        private List<Place_Vlog_Dto> place_vlogList;
//    }
    @Data
    @AllArgsConstructor
    static class Vlog_Dto{
        private String name;
        private String url;
    }
}
