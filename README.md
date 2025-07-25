SAGA-PATTERN

Saga Pattern, bir islemin (transaction) birden fazla mikroservisi kapsadigi durumlarda bu islemi parcalara ayirarak, her
parcayi ayri bir local transaction olarak ele alan bir veri tutarliligi desenidir.

Her adim basarili oldugunda bir sonraki adima gecilir. Eger bir adimda hata olusursa, onceki adimlarin etkisini geri
alacak islemler (compensating transaction) devreye girer ve sistem tutarliligi korunur.

Bu projede adımlar olarak:

1) Order servisi bir sipraiş oluşturduktan sonra bie event yayaınlıyor
2) Bu eventi payment servisi dinliyor. İşlemin kendine(sıranın) gelidiğini anlayıp bir event yayınlıyor.
3) İnventory servisi bu eventi dinliyor ve işlemini yapıp bir event yayınlıyor.
4) ...

her servis bir event yayınlıoyr ve bu eventleri dinleyen diğer servisler bu duruma göre işlemlerini devam ettiyor.
Eger bir hata alınırsa yazılan metodun bir cancel veya rollback metodu ile telafi edilen işlemi ile bir event
yayınlanır.
Fakat bu event geriye doğru yayınlanır. Bu şekilde veri tutarlılığı sağlanmış olur.

Örneğin inventory servisinde bir hata oluştuğunda yapılan işlem geri alınır(cancel) ve hemen bir event yayınlarak
payment serivisine burada bir hata oldu stok durumunu rollak yaptım der, payment servisi bu eventi dinler ödemeyi
geri iade eder(cancel) ve hemen bir event yayınlarak order'a ben bu ödemeyi iade ettim der,
Order servisi bu eventi dinleyerek sipraşi iptal eder

Her microservis kendi DB'sine sahiptir.
Servisler bağımsız çalışmalıdır. Böylece işlemler bağımsız adımlara bölünmeli ve geri alama adımları tanımlanmanlıdır.

COMPENSATING TRANSACTION:
Saga'da her adım için bir compensating transaction(telafi edici işlem) tanımlanır. Zincirde bir adım başarısız olursa,
önceki adımların etkisi geri alınır.

SAGA PATTERN YAKLASIMLARI:
1) Choreography:
   Her MS kendi işini tanımladığında bir event yayınlar.
   Diğer servisler bu eventi dinleyerek kendi adımarını başlatır.
    1) Orderservice -> sipraiş oluşturur => OrderCreateEvent
    2) InventoryService -> eventi dinler -> stok duser => StockReverseEvent
    3) PaymentServşce -> eventi dinler -> odeme alır => PaymentProcessEvent
    4) ShippingService -> eventi dinler => kargo baslatılır
    5) Eğer hata oluşursa hata eventi gönderilir ve telafi işlemleri devreye girer.
       Dağıtık bir yapı vardır. Burada her service kendi sorumluğunu bilir.
2) Orchestration:
   Tüm işlem adımları merkezi bir koordinator(saga orchestration) tarafından yönetilir.
   Koordinator gerekli serviceleri çağırır ve gerekirse compensating transaction'ı başlatır.
   Merkezi kontrol vardır. Tüm AKIŞI tek bir SERVİS yönetir.
   Her servis koordinatorü bilgilendirir.
    1) SagaOrchestration -> Orderservice
    2) Başarlıysa -> InventoryService
    3) Başarılıysa -> PaymentService
    4) Eğer hata olursa -> önceki servisler için geri alma işlemleri başlatır.



    


