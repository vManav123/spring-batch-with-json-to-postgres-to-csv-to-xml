package com.vmanav123.batch.listeners;

import com.vmanav123.batch.model.data.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("stepItemWriteListener")
public class StepItemWriteListener implements ItemWriteListener<UserEntity> {
	


	@Override
	public void beforeWrite(List<? extends UserEntity> list) {

	}

	@Override
	@SneakyThrows
	public void afterWrite(List<? extends UserEntity> list) {
	}

	@Override
	public void onWriteError(Exception e, List<? extends UserEntity> list) {

	}
}