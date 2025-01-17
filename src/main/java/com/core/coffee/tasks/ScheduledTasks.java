package com.core.coffee.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    // Expresión cron para ejecutar al final de cada mes a las 23:59:59
    /*
     * La expresión cron "59 59 23 L * ?" se desglosa de la siguiente manera:
        59: Segundo (59 segundos).
        59: Minuto (59 minutos).
        23: Hora (23 horas, es decir, 11 PM).
        L: Día del mes (último día del mes).
        *: Mes (cualquier mes).
        ?: Día de la semana (no especificado).
     */
  //  @Scheduled(cron = "59 59 23 L * ?")
    public void performTaskAtEndOfMonth() {
        LOGGER.info("Tarea programada ejecutada al final del mes");
        // Lógica de la tarea
    }

    

    // Expresión cron para ejecutar cada 10 segundos
 //   @Scheduled(cron = "*/10 * * * * ?")
    public void performTaskEvery10Seconds() {
        LOGGER.info("Tarea programada ejecutada cada 10 segundos");
        // Lógica de la tarea
    }

    // Expresión cron para ejecutar cada 5 minutos
  //  @Scheduled(cron = "0 */5 * * * ?")
    public void performTaskEvery5Minutes() {
        LOGGER.info("Tarea programada ejecutada cada 5 minutos");
        // Lógica de la tarea
    }
}
