package kamysh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlRootElement
public class ValidationErrorDTO {
    Map<String, String> message;
    String error;
}
