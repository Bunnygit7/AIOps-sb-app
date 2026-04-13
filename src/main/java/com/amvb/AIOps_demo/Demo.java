package com.amvb.AIOps_demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("demo")
public class Demo {
    private static final Logger logger=LoggerFactory.getLogger(Demo.class);
    @GetMapping
    public String demo() throws InterruptedException {
        logger.info("demo");
        Thread.sleep(100);
        return "hi";
    }

    @GetMapping("/slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(5000);
        logger.info("slow");
        return "slow Response";
    }

    @GetMapping("/error")
    public String errorEndpoint(){
        logger.info("error");
        throw new RuntimeException("error Response");
    }

    @GetMapping("/cpu-spike")
    public String cpuSpike(){
        long sum=0;
        for(int i=0;i<1000000000;i++){
            sum+=i;
        }
        logger.info("cpu-spike:"+sum);
        return "CPU spike :"+sum;
    }

    @GetMapping("/memory-leak")
    public String memoryLeak() {
        long before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // allocate
        List<byte[]> leak = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            leak.add(new byte[1024 * 1024]); // 1MB each
        }

        long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long usedMB = used / (1024 * 1024);

        logger.warn("memory-leak triggered usedMemoryMB=" + usedMB);

        return "Memory Leak (" + usedMB + " MB added)";
    }



}
