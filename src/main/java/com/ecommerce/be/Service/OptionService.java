package com.ecommerce.be.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Entity.Option;
import com.ecommerce.be.Repository.OptionRepository;

@Service
public class OptionService {
    
    private OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> getRootOptions() {
        return optionRepository.findByParentOptionOptionId(0);
    }

    public List<Option> getChildOptions(String parentOptionName) {
        return optionRepository.findChildOptionsByOptionName(parentOptionName);
    }

}
