package com.example.demo.hessian;


import java.util.List;
import java.util.UUID;

public interface Message {
    byte[] transferWithHessian(UUID id);
}
