package com.skala.nav7.api.direction.controller;

import com.skala.nav7.api.direction.converter.DirectionConverter;
import com.skala.nav7.api.direction.dto.request.DirectionRequestDTO;
import com.skala.nav7.api.direction.dto.response.DirectionResponseDTO;
import com.skala.nav7.api.direction.error.DirectionSuccessCode;
import com.skala.nav7.api.direction.service.DirectionService;
import com.skala.nav7.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/directions")
@Tag(name = "Direction 관련 API", description = "direction 관련 API입니다.")
public class DirectionController {

    private final DirectionService directionService;

    @Operation(summary = "Direction 생성", description = "새로운 Direction 을 생성합니다.")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DirectionResponseDTO.DefaultInfoDTO>> createNewDirections(
            @Parameter(description = "Direction 생성") @RequestBody DirectionRequestDTO.CreateDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(DirectionSuccessCode.DIRECTION_CREATED,
                        DirectionConverter.to(directionService.createNewDirection(dto))));
    }

    @Operation(summary = "Direction 최신 1개 조회", description = "가장 최신 Direction 을 찾습니다.")
    @GetMapping("")
    public ApiResponse<DirectionResponseDTO.DefaultInfoDTO> getCertifications(
    ) {
        return ApiResponse.onSuccess(DirectionConverter.to(directionService.findRecentOneDirection()));
    }

    @Operation(
            summary = "Direction 전체 조회",
            description = "전체 Direction 을 조회합니다."
    )
    @GetMapping("/all")
    public ApiResponse<List<DirectionResponseDTO.DefaultInfoDTO>> getAll(
    ) {
        return ApiResponse.onSuccess(
                directionService.findAll().stream().map(DirectionConverter::to).collect(Collectors.toList()));
    }

}
