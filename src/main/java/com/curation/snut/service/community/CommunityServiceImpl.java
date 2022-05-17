package com.curation.snut.service.community;

import java.util.List;
import java.util.stream.Collectors;

import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.community.Community;
import com.curation.snut.repository.community.CommunityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public List<CommunityDTO> CommunityList() {
        List<Community> communityList = communityRepository.findAll();
        List<CommunityDTO> communityDTOList = communityList.stream()
                .map(comment -> entityToDTO(comment)).collect(Collectors.toList());
        return communityDTOList;
    }

    @Override
    public void write(CommunityDTO communityDTO) {
        Community community = dtoToEntity(communityDTO);
        communityRepository.save(community);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        communityRepository.deleteById(id);
    }

    @Override
    public List<CommunityDTO> searchTitle(String searchTitle) {
        List<Object[]> communityList = communityRepository.findByTitleContaining(searchTitle);
        List<CommunityDTO> communityDTOList = communityList.stream()
                .map(entity -> entityToDTO((Community) entity[0], (Long) entity[1])).collect(Collectors.toList());
        return communityDTOList;
    }

    @Override
    public List<CommunityDTO> communityListWithCnt() {
        List<Object[]> communityData = communityRepository.countList();

        List<CommunityDTO> result = communityData.stream()
                .map(entity -> entityToDTO((Community) entity[0], (Long) entity[1]))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<CommunityDTO> findMyCommu(String email) {
        List<Community> commu = communityRepository.findMyCommu(email);
        List<CommunityDTO> result = commu.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
        return result;
    }

}
