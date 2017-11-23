package com.charityconnector.serviceImpl;

import com.charityconnector.dao.CauseRepository;
import com.charityconnector.entity.Cause;
import com.charityconnector.service.CauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CauseServiceImpl implements CauseService {

    @Resource
    private CauseRepository causeRepository;

    @Autowired
    public CauseServiceImpl(CauseRepository causeRepository) {
        this.causeRepository = causeRepository;
    }

    @Override
    public Cause findById(Long id) {
        return causeRepository.findOne(id);
    }

    @Override
    public List<Cause> getAllCauses() {
        return  causeRepository.findAll();
    }
}
