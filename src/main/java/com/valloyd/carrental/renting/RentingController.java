package com.valloyd.carrental.renting;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rentings")
public final class RentingController {
    private final RentingService rentingService;

    public RentingController(RentingService rentingService) {
        this.rentingService = rentingService;
    }

    @GetMapping
    public List<Renting> getRentings() {
        return rentingService.getRentings();
    }

    @GetMapping("/findId/{rentingId}")
    public Renting getRentingById(
            @PathVariable("rentingId") String rentingId
    ) {
        return rentingService.getRentingById(rentingId);
    }

    @PostMapping("/addRenting")
    public void addRenting(
            @RequestBody RentingDataRequest request
    ) {
        rentingService.addRenting(request);
    }

    @PutMapping("/updateRenting/{rentingId}")
    public void updateRenting(
            @PathVariable("rentingId") String rentingId,
            @RequestBody RentingDataRequest request
    ) {
        rentingService.updateRenting(rentingId, request);
    }

    @DeleteMapping("/deleteRenting/{rentingId}")
    public void deleteRenting(
            @PathVariable("rentingId") String rentingId
    ) {
        rentingService.deleteRenting(rentingId);
    }
}
