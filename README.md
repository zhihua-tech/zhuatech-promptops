# ZhuaTech PromptOps｜企业提示词运营与发布平台

[简体中文](README.md) | [English](README.en.md)

> 从提示词模板、变量、评测到审批发布，建立可版本化、可回归、可审计的企业 PromptOps 闭环。

ZhuaTech PromptOps 是 **[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)** 发布的企业 AI 提示词运营社区源码版，采用 Java 21、Spring Boot 4、Vue 3 和 MySQL 构建。

## 已实现的核心能力

- 模板变量解析和真实渲染，阻止缺失变量与未解析占位符发布。
- 检测“忽略系统指令、泄露系统提示、越权”等提示注入信号。
- 质量分、安全分、回归通过率、单次成本预算联合门禁。
- 提示词负责人和审批人职责分离，输出风险等级和审批路径。
- 请求幂等、不可重复发布评估、MySQL 审计记录与审计员查询。
- HTTP Basic + BCrypt 的管理员、运营员、审计员角色控制。
- Vue 管理台可提交真实评估请求并查看最近发布审计。

## 业务闭环

```text
模板设计 → 变量校验 → 注入扫描 → 回归评测 → 成本门禁 → 独立审批 → 发布/阻断 → 审计
```

## 主要接口

- `POST /api/promptops/releases/evaluate`：执行发布评估并持久化结果。
- `GET /api/promptops/audits`：查询最近 100 条审计记录。

## 本地启动

```bash
cp .env.example .env
docker compose up --build
```

访问 `http://localhost:8091`。演示环境请在 `.env` 设置账号密码；生产必须接入企业身份源、TLS 和密钥管理服务。

## 测试

```bash
cd backend && mvn test
cd frontend && npm install && npm run build
```

## 使用限制

> [!IMPORTANT]
> 本工程仅允许个人、非商业性的学习、研究和技术交流，不得商用。企业内部使用、生产部署、SaaS、实施交付、培训、咨询、外包和二次销售均须取得上海如静知华信息科技有限公司书面授权。详见 [LICENSE](LICENSE)。

深度定制、企业 AI 转型、私有模型接入和商业授权，请联系[知华科技](https://www.zhuatech.cn/)。

## 提示词 A/B 实验发布

新增 `POST /api/promptops/experiments/decision`，综合双臂样本量、统计置信度、任务成功率、安全事件、P95 延迟、单位成本、护栏和负责人审批，输出 `PROMOTE / CONTINUE / STOP / ROLLBACK`，避免只看单一效果指标直接替换生产提示词。
