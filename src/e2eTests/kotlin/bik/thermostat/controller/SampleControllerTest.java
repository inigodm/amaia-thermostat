package bik.thermostat.controller;

import http.Petition;
import org.junit.jupiter.api.Test;

class SampleControllerTest {

  @Test
  void helloWorld() {
    Petition
        .to("http://localhost:8080/helloworld")
        .sendAGet()
        .assertThatResponseIsOk()
        .assertThatBodyContains("Hello world!");
  }
}
