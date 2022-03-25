package kamysh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarshipArgumentDTO implements Serializable {
    private Long starship;
    private Long spaceMarine;
}
