package com.ktds.azure.tenant.requestboard.util;

import com.ktds.azure.tenant.requestboard.dto.RequestBoardDto;
import com.ktds.azure.tenant.requestboard.dto.RequestBoardListDto;
import com.ktds.azure.tenant.requestboard.model.RequestBoard;

import java.time.LocalDateTime;

public class RequestBoardMapper {

    public static RequestBoardListDto toBoardListDto(RequestBoard requestBoard) {
        if (requestBoard == null) {
            return null;
        }

        LocalDateTime editDate = null;

        if (requestBoard.getCreateDate() != null) {
            editDate = requestBoard.getModifyDate().isAfter(requestBoard.getCreateDate())  ? requestBoard.getModifyDate() : requestBoard.getCreateDate();
        } else {
            editDate = requestBoard.getModifyDate();
        }


        return new RequestBoardListDto(requestBoard.getId(),
                requestBoard.getTitle(),
                requestBoard.getWriter(),
                editDate,
                requestBoard.getState());
    }


    public static RequestBoard toEntity(RequestBoardDto requestBoardDto) {

        if (requestBoardDto == null) {
            return null;
        }

        RequestBoard board = new RequestBoard();
        board.setId(requestBoardDto.getId());
        // 최초 등록시 제목은 생성해줌
        if (requestBoardDto.getTitle() == null || requestBoardDto.getTitle().trim().isEmpty()) {
            board.setTitle("["+ requestBoardDto.getType() + "] " + requestBoardDto.getProjectName());
        } else {
            board.setTitle(requestBoardDto.getTitle());
        }
        board.setWriter(requestBoardDto.getWriter());
        board.setProjectCode(requestBoardDto.getProjectCode());
        board.setProjectName(requestBoardDto.getProjectName());
        board.setPurpose(requestBoardDto.getPurpose());
        board.setStartDate(requestBoardDto.getStartDate());
        board.setEndDate(requestBoardDto.getEndDate());
        board.setBudgetManager(requestBoardDto.getBudgetManager());
        board.setBudgetManagerEmail(requestBoardDto.getBudgetManagerEmail());
        board.setOperationManager(requestBoardDto.getOperationManager());
        board.setOperationManagerEmail(requestBoardDto.getOperationManagerEmail());
        board.setBudget(requestBoardDto.getBudget());
        board.setAlert(requestBoardDto.getAlert());
        board.setAlertBudget(requestBoardDto.getAlertBudget());
        board.setManagementGroup(requestBoardDto.getManagementGroup());
        board.setIpCount(requestBoardDto.getIpCount());
        board.setType(requestBoardDto.getType());
        board.setState(requestBoardDto.getState());
        board.setBudgetLink(requestBoardDto.getBudgetLink());
        board.setRequest(requestBoardDto.getRequest());

        return board;
    }

    public static RequestBoardDto toDto(RequestBoard board) {
        if(board == null) {
            return null;
        }

        return new RequestBoardDto(board.getId(),
                board.getTitle(),
                board.getWriter(),
                board.getProjectCode(),
                board.getProjectName(),
                board.getPurpose(),
                board.getStartDate(),
                board.getEndDate(),
                board.getBudgetManager(),
                board.getBudgetManagerEmail(),
                board.getOperationManager(),
                board.getOperationManagerEmail(),
                board.getBudget(),
                board.getAlert(),
                board.getAlertBudget(),
                board.getManagementGroup(),
                board.getIpCount(),
                board.getType(),
                board.getState(),
                board.getBudgetLink(),
                board.getRequest()
                );
    }
}
