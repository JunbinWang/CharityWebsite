package com.charityconnector.serviceImpl;

import com.charityconnector.dao.CauseRepository;
import com.charityconnector.dao.CharityRepository;
import com.charityconnector.dao.CountryRepository;
import com.charityconnector.dao.DonorRepository;
import com.charityconnector.entity.Cause;
import com.charityconnector.entity.Charity;
import com.charityconnector.entity.Country;
import com.charityconnector.entity.Donor;
import com.charityconnector.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class CharityServiceImpl implements CharityService {

    @Resource
    private CharityRepository charityRepository;

    @Resource
    private CauseRepository causeRepository;

    @Resource
    private CountryRepository countryRepository;

    @Resource
    private DonorRepository donorRepository;

    @Autowired
    public CharityServiceImpl(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    @Override
    public Charity getCharity() {
        return charityRepository.findOne(1L);
    }

    @Override
    public Charity addCharity(Charity charity) {
        Charity c = charityRepository.save(charity);
        return c;
    }

    @Override
    public Charity[] findByName(String name) {
        Charity[] charities = charityRepository.findByNameLike(name);
        return charities;
    }


    @Override
    public Charity[] findByNameLike(String name) {
        Charity[] charities = charityRepository.findByNameLike(name);
        return charities;
    }

    @Override
    public Charity findRandom() {
        return charityRepository.findRandom();
    }

    @Override
    public void updateSelective(Charity charity) {
        Charity readyToUpdate;
        if (charity.getId() == null) {
            return;
        } else {
            Charity origin = charityRepository.findOne(charity.getId());
            readyToUpdate = origin;

            String des = charity.getDescription() == null ? origin.getDescription() : charity.getDescription();
            readyToUpdate.setDescription(des);

            String name = charity.getName() == null ? origin.getName() : charity.getName();
            readyToUpdate.setName(name);

            String logoPath = charity.getLogoFile() == null ? origin.getLogoFile() : charity.getLogoFile();
            readyToUpdate.setLogoFile(logoPath);

            String email = charity.getEmail() == null ? origin.getEmail() : charity.getEmail();
            readyToUpdate.setEmail(email);

            charityRepository.save(readyToUpdate);
        }
    }

    @Override
    public void updateDirect(Charity charity) {
        charityRepository.save(charity);
    }

    @Override
    public void deleteById(Long id) {
        charityRepository.delete(id);
    }

    @Override
    public Charity findById(Long id) {
        return charityRepository.findOne(id);
    }

    @Override
    public Charity findByOauthUserId(String oauthUserId) {
        return charityRepository.findByOauthUserId(oauthUserId);
    }

    @Override
    public Charity[] findPaged(Pageable pageable) {
        Page<Charity> page = charityRepository.findAll(pageable);
        return page.getContent().toArray(new Charity[0]);
    }

    @Override
    public Page<Charity> findByNameLike(String name, Pageable pageable) {
        Page<Charity> page = charityRepository.findByNameLike(name, pageable);
        return page;
    }

    @Override
    public Charity[] findByNameOrDescriptionLike(String name) {
        Charity[] charity = charityRepository.findByNameOrDescriptionLike(name);
        return charity;
    }

    @Override
    public Set<Charity> getCharitiesByCause(String cause) {
        Cause res = causeRepository.findCauseByName(cause);
        return res.getCharities();
    }

    @Override
    public Set<Charity> getCharitiesByCountry(String country) {
        Country res = countryRepository.findCountryByCountryValue(country);
        return res.getCharities();
    }

    @Override
    public Page<Charity> findAll(Pageable pageable) {
        return charityRepository.findAll(pageable);
    }

    @Override
    public List<Charity> findAll(Sort sort) {
        return charityRepository.findAll(sort);
    }

    @Override
    public Charity thumbUp(Long id) {
        Charity charity = findById(id);
        charity.setThumbUp(charity.getThumbUp()+1);
        updateDirect(charity);
        return findById(id);
    }

    @Override
    public int thumbUpUnique(Long charityId, String donorOauthId) {
        int res = 0;
        Charity charity = charityRepository.getOne(charityId);
        Set<Donor> donors = charity.getThumbUpDonors();
        for (Donor donor : donors) {
            if (donor.getOauthId().equals(donorOauthId)) {
                res = -2; // represent this donor has thumbed up
                break;
            }
        }
        if (res != 0) {
            return res;
        }
        // only charity side own the relationship
        Donor donor = donorRepository.findByOauthId(donorOauthId);
        charity.addThumbUpDonor(donor);
        charityRepository.save(charity);
        res = charity.getThumbUpDonors().size();
        return res;
    }

    @Override
    public int getCharityThumbsUpById(Long id) {
        Charity charity = findById(id);
        return charity.getThumbUpDonors().size();
    }

    public Charity[] findByCauseAndCountry(Cause  cause, Country country, String name) {
        return charityRepository.findByCauseAndCountry(cause,country,name);
    }

    @Override
    public Charity[] findByCountry(Country country, String name) {
        return charityRepository.findByCountry(country,name);
    }

    @Override
    public Charity[] findByCause(Cause  cause,String name) {
        return charityRepository.findByCause(cause,name);
    }
}
