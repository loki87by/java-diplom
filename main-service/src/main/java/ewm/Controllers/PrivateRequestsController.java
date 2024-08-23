package ewm.Controllers;

import ewm.Entityes.EventRequest;
import ewm.Services.RequestService;
import ewm.Errors.ConflictException;
import ewm.Errors.ValidationException;
import ewm.main_service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestsController {
    private final RequestService service;
    private final Utils utils;

    //PRIVATE
    @GetMapping("")
    public List<EventRequest> getRequests(
            @PathVariable Object userId) {
        Long id = utils.idValidation(userId);
        return service.getRequests(id);
    }
    @PostMapping("")
    public EventRequest setRequest(
            @PathVariable Object userId,
            @RequestParam Object eventId) {
        Long uId = utils.idValidation(userId);
        Long eId = utils.idValidation(eventId);
        EventRequest oldReq = service.findRequest(uId, eId);

        if(oldReq != null) {
            throw new ConflictException("Дубликат запроса");
        }

        if(service.checkOwner(uId, eId)) {
            throw new ValidationException("Not owner");
        }

        if(!service.checkPublished(eId)) {
            throw new ValidationException("Not public");
        }

        if(!service.checkLimit(eId)) {
            throw new ConflictException("over limit");
        }

        return service.setRequest(uId, eId);
    }

    @PatchMapping("/{requestId}/cancel")
    public EventRequest cancelRequest(
            @PathVariable Object userId,
            @PathVariable Object requestId) {
        Long uId = utils.idValidation(userId);
        Long rId = utils.idValidation(requestId);
        return service.cancelRequest(uId, rId);
    }
}