package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DeliveryBoyRepository;

import domain.DeliveryBoy;



@Component
@Transactional
public class StringToDeliveryBoyConverter implements Converter<String,DeliveryBoy> {

	@Autowired
	DeliveryBoyRepository repository;
	
	@Override
	public DeliveryBoy convert(String s) {
		DeliveryBoy res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
