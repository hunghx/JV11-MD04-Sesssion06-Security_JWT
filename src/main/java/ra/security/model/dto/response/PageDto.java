package ra.security.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageDto <T>{
    private Integer first,prev, next, last, pages;
    private Long items;
    private List<T> data;

}
