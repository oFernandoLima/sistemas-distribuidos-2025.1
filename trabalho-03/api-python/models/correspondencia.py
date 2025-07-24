from pydantic import BaseModel
from typing import Dict


class Correspondencia(BaseModel):
    """Modelo base para correspondências"""

    codigo: str
    destinatario: str
    endereco: str
    tipo: str


class Carta(Correspondencia):
    """Modelo para cartas"""

    selada: bool
    tipo: str = "carta"


class Encomenda(Correspondencia):
    """Modelo para encomendas"""

    peso: float
    tipo: str = "encomenda"


class Telegrama(Correspondencia):
    """Modelo para telegramas"""

    numero_palavras: int
    tipo: str = "telegrama"


class CorrespondenciaResponse(BaseModel):
    """Modelo de resposta para correspondências"""

    codigo: str
    destinatario: str
    endereco: str
    tipo: str
    preco: float
    detalhes: Dict
