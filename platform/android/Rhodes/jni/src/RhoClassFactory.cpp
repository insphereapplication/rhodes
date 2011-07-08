#include <net/CURLNetRequest.h>
#include <common/PosixThreadImpl.h>

#include "rhodes/RhoClassFactory.h"
#include "rhodes/sslimpl.h"
#include "rhodes/rhocryptimpl.h"

namespace rho
{
namespace common
{

net::INetRequestImpl* CRhoClassFactory::createNetRequestImpl()
{
    return new net::CURLNetRequest();
}

IRhoThreadImpl *CRhoClassFactory::createThreadImpl()
{
    return new CPosixThreadImpl();
}

net::ISSL *CRhoClassFactory::createSSLEngine()
{
    if(!m_pSsl)
    {
        CMutexLock lock(m_sslMutex);
        if(!m_pSsl)
            m_pSsl = new net::SSLImpl();
    }
    return m_pSsl;
}

IRhoCrypt *CRhoClassFactory::createRhoCrypt()
{
    return new CRhoCryptImpl;
}

} // namespace common
} // namespace rho

