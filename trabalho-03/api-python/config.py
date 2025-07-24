from typing import Optional
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Configurações da aplicação"""

    # API Configuration
    title: str = "Sistema de Correios API"
    description: str = "API para gerenciamento de correspondências"
    version: str = "1.0.0"

    # Server Configuration
    host: str = "0.0.0.0"
    port: int = 8000
    debug: bool = False

    # CORS Configuration
    cors_origins: list = ["*"]
    cors_methods: list = ["*"]
    cors_headers: list = ["*"]

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


# Instância global das configurações
settings = Settings()
