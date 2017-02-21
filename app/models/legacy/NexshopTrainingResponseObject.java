package models.legacy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter //for Jackson serialization
public class NexshopTrainingResponseObject<T> {
    private T data;
}
