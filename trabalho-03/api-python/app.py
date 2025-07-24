from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

from config import settings
from routes import loja_router, correspondencias_router


def create_app() -> FastAPI:
    """Factory function para criar a aplicação FastAPI"""

    app = FastAPI(
        title=settings.title,
        description=settings.description,
        version=settings.version,
    )

    # Configurar CORS
    app.add_middleware(
        CORSMiddleware,
        allow_origins=settings.cors_origins,
        allow_credentials=True,
        allow_methods=settings.cors_methods,
        allow_headers=settings.cors_headers,
    )

    # Registrar rotas
    app.include_router(loja_router)
    app.include_router(correspondencias_router)

    # Rota raiz
    @app.get("/", tags=["root"])
    async def root():
        return {"message": "Sistema de Correios API"}

    return app


# Criar a aplicação
app = create_app()


if __name__ == "__main__":
    uvicorn.run(
        "main:app", host=settings.host, port=settings.port, reload=settings.debug
    )
