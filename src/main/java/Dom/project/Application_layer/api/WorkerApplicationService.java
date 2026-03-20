package Dom.project.Application_layer.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Dom.project.Web_layer.api.dto.WorkerDto;

@Service   
public class WorkerApplicationService {
    
    // временные заглушки 
    public List<WorkerDto> getAllWorkers() {
        return new ArrayList<>();   
    }
    
    public WorkerDto getWorkerById(Long id) {
        return null;   
    }
    
    public WorkerDto createWorker(WorkerDto workerDto) {
        return workerDto;
    }
    
    public WorkerDto updateWorker(Long id, WorkerDto workerDto) {
        return workerDto;
    }
    
    public void deleteWorker(Long id) {
         
    }
}