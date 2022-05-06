package com.curation.snut.service.memberlike;

public interface MemberCollectionLikeService {

    public void likes(Long mcolId, String email);
    public void unlikes(Long mcolId, String email);
}
