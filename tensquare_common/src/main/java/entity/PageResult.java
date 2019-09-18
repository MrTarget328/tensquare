package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Long totals;

    private List<T> rows;
}
