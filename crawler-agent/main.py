from crawler_agent.config import CrawlerConfig
from crawler_agent.services.crawler import CrawlerAgentService


def main() -> None:
    config = CrawlerConfig.from_environment()
    service = CrawlerAgentService(config)
    service.start()


if __name__ == "__main__":
    main()
