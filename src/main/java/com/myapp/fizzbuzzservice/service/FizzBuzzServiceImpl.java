package com.myapp.fizzbuzzservice.service;

import com.myapp.fizzbuzzservice.dto.ParameterDto;
import com.myapp.fizzbuzzservice.exception.FizzBuzzServiceException;
import com.myapp.fizzbuzzservice.persistence.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author shantanuk
 * <p>
 * Implementation of service {@link FizzBuzzService}
 */
@Service
public class FizzBuzzServiceImpl implements FizzBuzzService {

    private static final String SEPARATOR = "_";
    @Autowired
    private ParameterRepository parameterRepository;

    /**
     * @see FizzBuzzService#createFizzBuzzPattern(int, int, int, String, String)
     */
    @Override
    public String createFizzBuzzPattern(
            int param1,
            int param2,
            int limit,
            String str1,
            String str2
    ) throws FizzBuzzServiceException {
        validateInputParameters(param1, param2, limit);
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            if ((i % (param1 * param2)) == 0) {
                builder.append(str1 + str2).append(",");
            } else if (i % param1 == 0) {
                builder.append(str1).append(",");
            } else if (i % param2 == 0) {
                builder.append(str2).append(",");
            } else {
                builder.append(i).append(",");
            }
        }

        // save the details to database
        saveToDb(param1, param2, limit, str1, str2);

        return builder.substring(0, builder.length() - 1);
    }

    /**
     * @see FizzBuzzService#getStatistics()
     */
    @Override
    public String getStatistics() {
        try {
            List<ParameterDto> parameterDtoList = parameterRepository.findAll(Sort.by(Sort.Direction.DESC, "count"));
            ParameterDto dto = parameterDtoList.stream().findFirst().get();
            return String.format("Param1: %d Param2: %d Limit: %d Str1: %s Str2: %s with Count: %d",
                    dto.getParam1(),
                    dto.getParam2(),
                    dto.getLimit(),
                    dto.getStr1(),
                    dto.getStr2(),
                    dto.getCount()
            );
        } catch (NoSuchElementException exception) {
            throw new IllegalStateException("There are no records in the database.");
        }
    }

    private String saveToDb(int param1, int param2, int limit, String str1, String str2) {
        ParameterDto parameterDto = new ParameterDto();
        parameterDto.setParam1(param1);
        parameterDto.setParam2(param2);
        parameterDto.setLimit(limit);
        parameterDto.setStr1(str1);
        parameterDto.setStr2(str2);
        String primaryKey = param1 + SEPARATOR + param2 + SEPARATOR + limit + SEPARATOR + str1 + SEPARATOR + str2;
        parameterDto.setId(primaryKey);
        ParameterDto parameterDtoFromDb = getParameterFromDb(primaryKey);
        if (parameterDtoFromDb != null) {
            parameterDto.setCount(parameterDtoFromDb.getCount() + 1);
        } else {
            parameterDto.setCount(1);
        }
        parameterRepository.save(parameterDto); // save to database
        return parameterDto.getId();
    }

    public void validateInputParameters(int param1, int param2, int limit) throws FizzBuzzServiceException {
        if (param1 > limit) {
            throw new FizzBuzzServiceException("Param1 cannot be greater than limit.");
        }
        if (param2 > limit) {
            throw new FizzBuzzServiceException("Param2 cannot be greater than limit.");
        }
    }

    public ParameterDto getParameterFromDb(String id) {
        return parameterRepository.findById(id)
                .orElseGet(ParameterDto::new);
    }
}
