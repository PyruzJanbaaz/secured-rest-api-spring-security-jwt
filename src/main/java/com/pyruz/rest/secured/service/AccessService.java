package com.pyruz.rest.secured.service;

import com.pyruz.rest.secured.configuration.ApplicationContextHolder;
import com.pyruz.rest.secured.exception.ServiceException;
import com.pyruz.rest.secured.model.domain.AddNewAccessBean;
import com.pyruz.rest.secured.model.domain.UpdateAccessBean;
import com.pyruz.rest.secured.model.dto.AccessDTO;
import com.pyruz.rest.secured.model.dto.BaseDTO;
import com.pyruz.rest.secured.model.dto.MetaDTO;
import com.pyruz.rest.secured.model.entity.Access;
import com.pyruz.rest.secured.model.entity.Api;
import com.pyruz.rest.secured.model.mapper.AccessMapper;
import com.pyruz.rest.secured.repository.AccessRepository;
import com.pyruz.rest.secured.repository.ApiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccessService extends ApplicationContextHolder {

    final AccessRepository accessRepository;
    final ApiRepository menuRepository;
    final AccessMapper accessMapper;

    public AccessService(AccessRepository accessRepository, ApiRepository menuRepository, AccessMapper accessMapper) {
        this.accessRepository = accessRepository;
        this.menuRepository = menuRepository;
        this.accessMapper = accessMapper;
    }


    public BaseDTO addAccess(AddNewAccessBean addNewAccessBean) {
        if (accessRepository.existsAccessByTitleIgnoreCase(addNewAccessBean.getTitle()).equals(true)) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.duplicateTitle.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        Access access = accessMapper.addNewAccessBeanToAccess(addNewAccessBean);
        access.setApis(getAccessApi(addNewAccessBean.getApiIds()));
        accessRepository.save(access);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessMapper.accessToAccessDTO(access))
                .build();
    }

    public BaseDTO editAccess(UpdateAccessBean updateAccessBean) {
        Access access = getAccessEntity(updateAccessBean.getId());
        if (!updateAccessBean.getTitle().equalsIgnoreCase(access.getTitle()) && accessRepository.existsAccessByTitleIgnoreCase(updateAccessBean.getTitle()).equals(true)) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.duplicateTitle.text"))
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        access = accessMapper.updateAccessBeanToAccess(access, updateAccessBean);
        access.setApis(getAccessApi(updateAccessBean.getApiIds()));
        accessRepository.save(access);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessMapper.accessToAccessDTO(access))
                .build();
    }

    public BaseDTO getAllAccesses(Integer pageNumber, Integer pageSize) {
        Page<Access> result = accessRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<AccessDTO> accessDTOList = new ArrayList<>();
        result.forEach(access -> accessDTOList.add(accessMapper.accessToAccessDTO(access)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessDTOList)
                .build();
    }

    public BaseDTO getAllAccesses() {
        List<Access> result = accessRepository.findAll();
        List<AccessDTO> accessDTOList = new ArrayList<>();
        result.forEach(access -> accessDTOList.add(accessMapper.accessToAccessDTO(access)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessDTOList)
                .build();
    }

    public BaseDTO getAccess(Long id) {
        Access access = getAccessEntity(id);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessMapper.accessToAccessDTO(access))
                .build();
    }

    public BaseDTO deleteAccess(Long id) {
        Access access = getAccessEntity(id);
        access.setIsDeleted(true);
        accessRepository.save(access);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(accessMapper.accessToAccessDTO(access))
                .build();
    }

    private Access getAccessEntity(Long id) {
        Optional<Access> access = accessRepository.findById(id);
        if (!access.isPresent()) {
            throw ServiceException.builder()
                    .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
        return access.get();
    }

    private List<Api> getAccessApi(List<Long> listIds) {
        List<Api> apis = null;
        if (listIds != null && !listIds.isEmpty()) {
            apis = menuRepository.findApiByIdIn(listIds);
        }
        return apis;
    }
}