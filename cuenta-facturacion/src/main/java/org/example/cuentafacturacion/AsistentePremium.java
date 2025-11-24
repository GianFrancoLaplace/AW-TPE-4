package org.example.cuentafacturacion;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AsistentePremium {
    @SystemMessage("""
        Eres un asistente útil para usuarios PREMIUM de una app de movilidad.
        Tu tono es profesional pero amable.
        
        Tienes acceso a herramientas para consultar saldos, reportes de uso y cotizar viajes.
        Si el usuario pregunta algo que requiere datos privados (como saldo), SIEMPRE usa la herramienta correspondiente.
        Si el usuario pregunta algo fuera de tu alcance, indícalo educadamente.
        
        IMPORTANTE: Hoy es {{current_date}}.
    """)
    String chatear(String mensajeUsuario);

}
