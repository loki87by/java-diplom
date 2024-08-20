package ewm.Controllers;

import ewm.Objects.EventRequest;
import ewm.Services.RequestService;
import ewm.Utils.ConflictException;
import ewm.Utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestsController {
    private final RequestService service;

    //PRIVATE
    @GetMapping("/")
    public List<EventRequest> getRequests(
            @PathVariable Long userId) {
        return service.getRequests(userId);
    }
    @PostMapping("/")
    public EventRequest setRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId) {
        EventRequest oldReq = service.findRequest(userId, eventId);

        if(oldReq != null) {
            throw new ConflictException("Дубликат запроса");
        }

        if(service.checkOwner(userId, eventId)) {
            throw new ValidationException("Not owner");
        }

        if(!service.checkPublished(eventId)) {
            throw new ValidationException("Not public");
        }

        if(!service.checkLimit(eventId)) {
            throw new ConflictException("over limit");
        }

        return service.setRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public EventRequest cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
}