package models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Image {
    String name;
    String path;
}
