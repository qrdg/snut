package com.curation.snut.service;

import com.curation.snut.entity.Hashtag;
import com.curation.snut.repository.CurationRepository;
import com.curation.snut.repository.HashtagRepository;
import com.curation.snut.repository.SnutCollectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class HashTagServiceImpl implements HashTagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public void upCountHashtag(String word) {
        List<Hashtag> hashtag = hashtagRepository.findHashtagsByWord(word);
        hashtag.stream().forEach(tag -> {
            tag.upCount();
            hashtagRepository.save(tag);
        });
    }
}
