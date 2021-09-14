package ganggang3.gang.Service;


import ganggang3.gang.Repository.PlaceRepository;
import ganggang3.gang.domain.Place;
import ganggang3.gang.domain.PlaceVlog;
import ganggang3.gang.domain.Vlog;
import ganggang3.gang.dto.PlaceDto;
import ganggang3.gang.dto.VlogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceVlog> findPlaceVlogList(long placeId) {
        return placeRepository.findPlaceVlogList(placeId);
    }

    public long find(long placeId) {
        return placeRepository.findOne(placeId).getId();
    }

    public List<Place> findPlaceList(long cityId, long categoryId) {
        return placeRepository.findPlace(cityId,categoryId);
    }

    private List<Place> findTop5FromDb(long cityId, long categoryId) {
        List<Place> placeList=placeRepository.findPlace(cityId,categoryId);
        Collections.sort(placeList, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return Long.compare(o2.getPlace_vlogList().size(),o1.getPlace_vlogList().size());
            }
        });
        return placeList.subList(0,5);

    }
    public List<PlaceDto> getTop5(long city_id, long category_id) {
        List<Place> findTop5 = findTop5FromDb(city_id, category_id);
        List<PlaceDto> placeDtoList = new ArrayList<>();
        findTop5.forEach(p -> {
            //Top5 place 의 vlog_list
            List<VlogDto> vlog_list = getVlogList(p.getPlace_vlogList());
            //Place Dto 할당
            PlaceDto pd = PlaceDto.of(p, vlog_list);
            //리스트에 할당
            placeDtoList.add(pd);
        });

        return placeDtoList;
    }

    private List<VlogDto> getVlogList(List<PlaceVlog> p_v_list) {
        List<VlogDto> list = new ArrayList<>();
        for(int j = 0; j< p_v_list.size(); j++){
            PlaceVlog pv = p_v_list.get(j);
            Vlog v = pv.getVlog();
            //Top5 place의 Vlog DTO를 할당
            list.add(
                    new VlogDto(
                            v.getName(),
                            v.getUrl()
                    ));
        }
        return list;
    }
    public List<Vlog> findVlogList(long placeId) {
        List<PlaceVlog> placeVlogList=findPlaceVlogList(placeId);
        List<Vlog> vlogList=new ArrayList<>();
        for(int i=0;i<placeVlogList.size();i++){
            PlaceVlog placeVlog=placeVlogList.get(i);
            vlogList.add(placeVlog.getVlog());
        }
        return vlogList;
    }
}
