from fastapi import APIRouter

from models import Carta, Encomenda, Telegrama
from services import correspondencia_service

router = APIRouter(prefix="/correspondencias", tags=["correspondencias"])


@router.post("/carta")
async def registrar_carta(carta: Carta):
    """Registra uma nova carta"""
    return correspondencia_service.registrar_carta(carta)


@router.post("/encomenda")
async def registrar_encomenda(encomenda: Encomenda):
    """Registra uma nova encomenda"""
    return correspondencia_service.registrar_encomenda(encomenda)


@router.post("/telegrama")
async def registrar_telegrama(telegrama: Telegrama):
    """Registra um novo telegrama"""
    return correspondencia_service.registrar_telegrama(telegrama)


@router.get("")
async def listar_correspondencias():
    """Lista todas as correspondências cadastradas"""
    return correspondencia_service.listar_correspondencias()


@router.get("/{codigo}")
async def consultar_correspondencia(codigo: str):
    """Consulta uma correspondência pelo código"""
    return correspondencia_service.consultar_correspondencia(codigo)


@router.get("/{codigo}/preco")
async def consultar_preco(codigo: str):
    """Consulta o preço de uma correspondência pelo código"""
    return correspondencia_service.consultar_preco(codigo)


@router.delete("/{codigo}")
async def entregar_correspondencia(codigo: str):
    """Remove correspondência do sistema (entrega)"""
    return correspondencia_service.entregar_correspondencia(codigo)
