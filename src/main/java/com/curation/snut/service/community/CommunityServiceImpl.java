package com.curation.snut.service.community;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.curation.snut.dto.PageRequestDTO;
import com.curation.snut.dto.PageResultDTO;
import com.curation.snut.dto.community.CommunityDTO;
import com.curation.snut.entity.community.Community;
import com.curation.snut.repository.community.CommentRepository;
import com.curation.snut.repository.community.CommunityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommunityDTO getCommunityData(Long no) {
        Community community = communityRepository.getCommunityDataByNo(no);
        return entityToDTO(community);
    }

    @Override
    public void modifyCommunityContent(Long no, String content) {
        Community community = communityRepository.getCommunityDataByNo(no);
        community.setText(content);
        communityRepository.save(community);
    }

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
        System.out.println("id >>>>>" + id);
        commentRepository.deleteAllCommunutyComment(id);
        communityRepository.deleteById(id);
    }

    @Override
    public PageResultDTO searchTitle(PageRequestDTO pageRequestDTO, String searchTitle) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("no").descending());
        Page<Object[]> communityList = communityRepository.findByTitleContaining(pageable, searchTitle);
        Function<Object[], CommunityDTO> fn = (en -> {
           return entityToDTO((Community) en[0], (Long) en[1]);
        });
        return new PageResultDTO<>(communityList, fn);
    }

    @Override
    public PageResultDTO communityListWithCnt(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("no").descending());
        Page<Object[]> communityList = communityRepository.countList(pageable);
        Function<Object[], CommunityDTO> fn = (en -> {
            return entityToDTO((Community) en[0], (Long) en[1]);
        });
        return new PageResultDTO<>(communityList, fn);
    }

    @Override
    public List<CommunityDTO> findMyCommu(String email) {
        List<Community> commu = communityRepository.findMyCommu(email);
        List<CommunityDTO> result = commu.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
        return result;
    }

}
