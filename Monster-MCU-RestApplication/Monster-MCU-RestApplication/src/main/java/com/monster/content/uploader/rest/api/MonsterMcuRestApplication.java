package com.monster.content.uploader.rest.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MonsterMcuRestApplication {

	private final static Logger logger = LoggerFactory.getLogger(MonsterMcuRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MonsterMcuRestApplication.class, args);

	}

}
