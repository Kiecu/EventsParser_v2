package Handlers;

public interface CommandHandler<T> {
    void Handle(T message);
}
