package com.charityconnector.serviceImpl;

import com.charityconnector.dao.ActivityRepository;
import com.charityconnector.dao.CharityRepository;
import com.charityconnector.dao.DonorRepository;
import com.charityconnector.entity.Activity;
import com.charityconnector.entity.Donor;
import com.charityconnector.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private CharityRepository charityRepository;

    @Resource
    private DonorRepository donorRepository;

    @Override
    public Activity findById(Long id) {
        return activityRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.delete(id);
    }

    @Override
    public Activity addActivity(Activity activity) {
        Date now = new Date();
        activity.setInsertTime(now);
        activity.setUpdateTime(now);
        return activityRepository.save(activity);
    }

    @Override
    public void updateSelective(Activity activity) {
        Activity readyToUpdate;
        if (activity.getId() == null) {
            return;
        } else {
            Activity origin = activityRepository.findOne(activity.getId());
            readyToUpdate = origin;
            readyToUpdate.setId(activity.getId());

            String title = activity.getTitle() == null ? origin.getTitle() : activity.getTitle();
            readyToUpdate.setTitle(title);

            String content = activity.getContent() == null ? origin.getContent() : activity.getContent();
            readyToUpdate.setContent(content);

            String country = activity.getCountry() == null ? origin.getCountry() : activity.getCountry();
            readyToUpdate.setCountry(country);

            Date holdDate = activity.getHoldDate() == null ? origin.getHoldDate() : activity.getHoldDate();
            readyToUpdate.setHoldDate(holdDate);
        }
        Date now = new Date();
        readyToUpdate.setUpdateTime(now);
        activityRepository.save(readyToUpdate);
    }

    @Override
    public void updateDirect(Activity activity) {
        Date now = new Date();
        activity.setUpdateTime(now);
        activityRepository.save(activity);
    }

    @Override
    public Activity[] findArticlesByCharityId(Long charityId) {
        Set<Activity> temp = charityRepository.findOne(charityId).getActivities();
        return temp.toArray(new Activity[temp.size()]);
    }

    @Override
    public int volunteer(Long activityId, Long donorId) {
        Activity activity = activityRepository.findOne(activityId);
        Set<Donor> donors = activity.getDonors();
        int res = 0;
        for (Donor donor : donors) {
            if (donor.getId().equals(donorId)) {
                res = -2; // represent this donor has thumbed up
                break;
            }
        }
        if (res != 0) {
            return res;
        }
        Donor donor = donorRepository.getOne(donorId);
        activity.addVolunteerDonor(donor);
        activityRepository.save(activity);
        return 0;
    }
}
