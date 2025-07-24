from fastapi import APIRouter

router = APIRouter(prefix="/loja", tags=["loja"])


@router.get("/nome")
async def obter_nome_loja():
    """Retorna o nome da loja"""
    return {"nome": "Correios UFC - Sistema Distribuído"}
