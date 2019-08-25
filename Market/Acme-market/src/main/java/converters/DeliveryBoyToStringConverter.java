package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DeliveryBoy;


@Component
@Transactional
public class DeliveryBoyToStringConverter implements Converter<DeliveryBoy,String> {

	@Override
	public String convert(DeliveryBoy o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
