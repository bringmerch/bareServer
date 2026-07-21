package core;

public interface Worker {
    Response execute(WorkOrder workOrder) throws BareException;
}
